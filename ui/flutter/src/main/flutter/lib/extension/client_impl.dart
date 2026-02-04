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

import 'package:cafemod/rpc/client.dart';
import 'package:cafemod/rpc/rpc.dart';
import 'package:cafemod/utility/opcodes.dart';
import 'package:json_annotation/json_annotation.dart';

part 'client_impl.g.dart';

extension ClientImpl on Client {
  Future<ZipEntry?> loadFiles(String path) async {
    final response = await send(
      Rpc(type: RpcType.REQUEST, method: "loadFiles", params: [path]),
    );

    if (response.result == null) return null;
    return ZipEntry.fromJson(jsonDecode(response.result!));
  }

  Future<ZipEntry?> findEntry(String path, int depth) async {
    final response = await send(
      Rpc(type: RpcType.REQUEST, method: "findEntry", params: [path, depth]),
    );

    if (response.result == null) return null;
    return ZipEntry.fromJson(jsonDecode(response.result!));
  }

  Future<String> getTracePrinted(String path) async {
    final response = await send(
      Rpc(type: RpcType.REQUEST, method: "getTracePrinted", params: [path]),
    );

    if (response.result == null) return "";
    return response.result ?? "";
  }

  Future<List<Field>> getFields(String path) async {
    final response = await send(
      Rpc(type: RpcType.REQUEST, method: "getFields", params: [path]),
    );

    if (response.result == null) return [];
    return (jsonDecode(response.result!) as List<dynamic>)
        .map((item) => Field.fromJson(item))
        .toList();
  }

  Future<List<Method>> getMethods(String path) async {
    final response = await send(
      Rpc(type: RpcType.REQUEST, method: "getMethods", params: [path]),
    );

    if (response.result == null) return [];
    return (jsonDecode(response.result!) as List<dynamic>)
        .map((item) => Method.fromJson(item))
        .toList();
  }

  Future<List<InstructionNode>> getMethodInstructions(
    String path,
    String nd,
  ) async {
    final response = await send(
      Rpc(
        type: RpcType.REQUEST,
        method: "getMethodInstructions",
        params: [path, nd],
      ),
    );

    if (response.result == null) return [];
    final jsonArray =
        jsonDecode(response.result!) as List<Map<String, dynamic>>;

    return jsonArray.map((json) {
      final node = InstructionNode.fromJson(json);
      switch (node.type) {
        case INT_INSN:
          return IntInstructionNode.fromJson(json);
        case VAR_INSN:
          return VarInstructionNode.fromJson(json);
        case TYPE_INSN:
          return TypeInstructionNode.fromJson(json);
        case FIELD_INSN:
          return FieldInstructionNode.fromJson(json);
        case METHOD_INSN:
          return MethodInstructionNode.fromJson(json);
        case INVOKE_DYNAMIC_INSN:
          return InvokeDynamicInstructionNode.fromJson(json);
        case JUMP_INSN:
          return JumpInstructionNode.fromJson(json);
        case LABEL:
          return LabelInstructionNode.fromJson(json);
        case LDC_INSN:
          return LdcInstructionNode.fromJson(json);
        case IINC_INSN:
          return IincInstructionNode.fromJson(json);
        case TABLESWITCH_INSN:
          return TableSwitchInstructionNode.fromJson(json);
        case LOOKUPSWITCH_INSN:
          return LookupSwitchInstructionNode.fromJson(json);
        case MULTIANEWARRAY_INSN:
          return MultiANewArrayInstructionNode.fromJson(json);
        case FRAME:
          return FrameInstructionNode.fromJson(json);
        case LINE:
          return LineNumberInstructionNode.fromJson(json);
        default:
          throw Exception("Unsupported instruction");
      }
    }).toList();
  }
}

@JsonSerializable()
class ZipEntry {
  final String name;
  final String path;
  final ZipEntryType type;
  final ZipEntry? parent;
  final List<ZipEntry>? children;

  const ZipEntry({
    required this.name,
    required this.path,
    required this.type,
    this.parent,
    this.children,
  });

  factory ZipEntry.fromJson(Map<String, dynamic> json) =>
      _$ZipEntryFromJson(json);

  Map<String, dynamic> toJson() => _$ZipEntryToJson(this);
}

enum ZipEntryType { FILE, DIRECTORY }

@JsonSerializable()
class Field {
  final String name;
  final String desc;
  final int access;

  const Field(this.name, this.desc, this.access);

  factory Field.fromJson(Map<String, dynamic> json) => _$FieldFromJson(json);

  Map<String, dynamic> toJson() => _$FieldToJson(this);
}

@JsonSerializable()
class Method {
  final String name;
  final String desc;
  final int access;

  const Method(this.name, this.desc, this.access);

  factory Method.fromJson(Map<String, dynamic> json) => _$MethodFromJson(json);

  Map<String, dynamic> toJson() => _$MethodToJson(this);
}

@JsonSerializable()
class InstructionNode {
  final int type;
  final int opcode;

  const InstructionNode(this.type, this.opcode);

  factory InstructionNode.fromJson(Map<String, dynamic> json) =>
      _$InstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$InstructionNodeToJson(this);
}

@JsonSerializable()
class FieldInstructionNode extends InstructionNode {
  final String owner;
  final String name;
  final String descriptor;

  FieldInstructionNode(
    super.type,
    super.opcode,
    this.owner,
    this.name,
    this.descriptor,
  );

  factory FieldInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$FieldInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$FieldInstructionNodeToJson(this);
}

@JsonSerializable()
class FrameInstructionNode extends InstructionNode {
  final int frame;
  final List<dynamic> local;
  final List<dynamic> stack;

  FrameInstructionNode(
    super.type,
    super.opcode,
    this.frame,
    this.local,
    this.stack,
  );

  factory FrameInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$FrameInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$FrameInstructionNodeToJson(this);
}

@JsonSerializable()
class IincInstructionNode extends InstructionNode {
  final int varIndex;
  final int increment;

  IincInstructionNode(super.type, super.opcode, this.varIndex, this.increment);

  factory IincInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$IincInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$IincInstructionNodeToJson(this);
}

@JsonSerializable()
class InsnInstructionNode extends InstructionNode {
  InsnInstructionNode(super.type, super.opcode);

  factory InsnInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$InsnInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$InsnInstructionNodeToJson(this);
}

@JsonSerializable()
class IntInstructionNode extends InstructionNode {
  final int operand;

  IntInstructionNode(super.type, super.opcode, this.operand);

  factory IntInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$IntInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$IntInstructionNodeToJson(this);
}

@JsonSerializable()
class InvokeDynamicInstructionNode extends InstructionNode {
  final String name;
  final String desc;

  InvokeDynamicInstructionNode(super.type, super.opcode, this.name, this.desc);

  factory InvokeDynamicInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$InvokeDynamicInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$InvokeDynamicInstructionNodeToJson(this);
}

@JsonSerializable()
class JumpInstructionNode extends InstructionNode {
  final int labelIndex;

  JumpInstructionNode(super.type, super.opcode, this.labelIndex);

  factory JumpInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$JumpInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$JumpInstructionNodeToJson(this);
}

@JsonSerializable()
class LabelInstructionNode extends InstructionNode {
  final int labelIndex;

  LabelInstructionNode(super.type, super.opcode, this.labelIndex);

  factory LabelInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$LabelInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$LabelInstructionNodeToJson(this);
}

@JsonSerializable()
class LdcInstructionNode extends InstructionNode {
  final dynamic cst;

  LdcInstructionNode(super.type, super.opcode, this.cst);

  factory LdcInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$LdcInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$LdcInstructionNodeToJson(this);
}

@JsonSerializable()
class LineNumberInstructionNode extends InstructionNode {
  final int line;
  final int startLabelIndex;

  LineNumberInstructionNode(
    super.type,
    super.opcode,
    this.line,
    this.startLabelIndex,
  );

  factory LineNumberInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$LineNumberInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$LineNumberInstructionNodeToJson(this);
}

@JsonSerializable()
class LookupSwitchInstructionNode extends InstructionNode {
  final int dfltLabelIndex;

  LookupSwitchInstructionNode(super.type, super.opcode, this.dfltLabelIndex);

  factory LookupSwitchInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$LookupSwitchInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$LookupSwitchInstructionNodeToJson(this);
}

@JsonSerializable()
class MethodInstructionNode extends InstructionNode {
  final String owner;
  final String name;
  final String desc;

  MethodInstructionNode(
    super.type,
    super.opcode,
    this.owner,
    this.name,
    this.desc,
  );

  factory MethodInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$MethodInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$MethodInstructionNodeToJson(this);
}

@JsonSerializable()
class MultiANewArrayInstructionNode extends InstructionNode {
  final String desc;
  final int dims;

  MultiANewArrayInstructionNode(super.type, super.opcode, this.desc, this.dims);

  factory MultiANewArrayInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$MultiANewArrayInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$MultiANewArrayInstructionNodeToJson(this);
}

@JsonSerializable()
class TableSwitchInstructionNode extends InstructionNode {
  final int min;
  final int max;
  final int dfltLabelIndex;
  final List<int> labelIndexes;

  TableSwitchInstructionNode(
    super.type,
    super.opcode,
    this.min,
    this.max,
    this.dfltLabelIndex,
    this.labelIndexes,
  );

  factory TableSwitchInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$TableSwitchInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$TableSwitchInstructionNodeToJson(this);
}

@JsonSerializable()
class TypeInstructionNode extends InstructionNode {
  final String desc;

  TypeInstructionNode(super.type, super.opcode, this.desc);

  factory TypeInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$TypeInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$TypeInstructionNodeToJson(this);
}

@JsonSerializable()
class VarInstructionNode extends InstructionNode {
  final int varIndex;

  VarInstructionNode(super.type, super.opcode, this.varIndex);

  factory VarInstructionNode.fromJson(Map<String, dynamic> json) =>
      _$VarInstructionNodeFromJson(json);

  Map<String, dynamic> toJson() => _$VarInstructionNodeToJson(this);
}
