/*
 * Copyright (c) 2026 Enaium
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:cafemod/rpc/rpc.dart';
import 'package:flutter/foundation.dart';
import 'package:injectable/injectable.dart';
import 'package:path/path.dart' as p;

@singleton
class Client {
  late final Process _proc;
  final StreamController<String> controller =
      StreamController<String>.broadcast();

  Future<void> start() async {
    _proc = await Process.start("java", [
      "-cp",
      if (kDebugMode)
        p.normalize(p.absolute("../../../build/libs/lib.jar"))
      else
        "lib.jar",
      "cn.enaium.cafemod.MainKt",
    ], mode: ProcessStartMode.detachedWithStdio);
    _proc.stdout.transform(utf8.decoder).transform(const LineSplitter()).listen(
      (data) {
        controller.add(data);
      },
    );

    _proc.stderr.transform(utf8.decoder).listen((data) {
      stderr.writeln(data);
    });
  }

  Future<void> stop() async {
    _proc.stdin.close();
    _proc.kill();
    await controller.close();
  }

  Future<Rpc> send(Rpc request) async {
    _proc.stdin.writeln(jsonEncode(request.toJson()));

    return controller.stream
        .map((data) => Rpc.fromJson(jsonDecode(data)))
        .firstWhere(
          (response) =>
              response.type == RpcType.RESPONSE &&
              response.method == request.method,
        );
  }
}
