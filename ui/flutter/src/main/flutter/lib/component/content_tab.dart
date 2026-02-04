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

import 'package:cafemod/di/di.dart';
import 'package:cafemod/extension/client_impl.dart';
import 'package:cafemod/rpc/client.dart';
import 'package:cafemod/state/content_tab_state.dart';
import 'package:fluent_ui/fluent_ui.dart';
import 'package:flutter/material.dart' hide Tab;
import 'package:flutter_syntax_view/flutter_syntax_view.dart';
import 'package:material_table_view/material_table_view.dart';
import 'package:provider/provider.dart';

class ContentTab extends StatefulWidget {
  const ContentTab({super.key});

  @override
  State<ContentTab> createState() => _ContentTabState();
}

class _ContentTabState extends State<ContentTab> {
  var currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Consumer<ContentTabState>(
      builder: (_, state, _) {
        return Container(
          decoration: BoxDecoration(
            border: Border.all(color: Color(0x10FFFFFF), width: 0.5),
            borderRadius: BorderRadius.circular(5),
          ),
          child: TabView(
            currentIndex: currentIndex,
            onChanged: (index) => setState(() => currentIndex = index),
            tabWidthBehavior: TabWidthBehavior.sizeToContent,
            closeButtonVisibility: CloseButtonVisibilityMode.always,
            tabs: state.entries
                .map(
                  (entry) => Tab(
                    text: Text(entry.name),
                    onClosed: () {
                      context.read<ContentTabState>().removeEntry(entry);
                    },
                    body: PaneBody(entry: entry),
                  ),
                )
                .toList(),
          ),
        );
      },
    );
  }
}

class PaneBody extends StatefulWidget {
  final ZipEntry entry;

  const PaneBody({super.key, required this.entry});

  @override
  State<PaneBody> createState() => _PaneBodyState();
}

class _PaneBodyState extends State<PaneBody> {
  @override
  Widget build(BuildContext context) {
    return widget.entry.name.endsWith(".class")
        ? ClassFileContent(entry: widget.entry)
        : Placeholder();
  }
}

class ClassFileContent extends StatefulWidget {
  final ZipEntry entry;

  const ClassFileContent({super.key, required this.entry});

  @override
  State<ClassFileContent> createState() => _ClassFileContentState();
}

class _ClassFileContentState extends State<ClassFileContent> {
  var currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return TabView(
      currentIndex: currentIndex,
      onChanged: (index) => setState(() => currentIndex = index),
      tabs: [
        Tab(
          text: Text("TracePrinted"),
          body: TracePrinted(entry: widget.entry),
        ),
        Tab(
          text: Text("Members"),
          body: ClassMembers(entry: widget.entry),
        ),
      ],
    );
  }
}

class TracePrinted extends StatefulWidget {
  final ZipEntry entry;

  const TracePrinted({super.key, required this.entry});

  @override
  State<TracePrinted> createState() => _TracePrintedState();
}

class _TracePrintedState extends State<TracePrinted> {
  final Client rpc = getIt<Client>();

  var content = "";

  @override
  void initState() {
    rpc.getTracePrinted(widget.entry.path).then((result) {
      setState(() {
        content = result;
      });
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return SyntaxView(
      code: content,
      withZoom: false,
      expanded: true,
      syntax: Syntax.RUST,
      syntaxTheme: SyntaxTheme.vscodeDark(),
      fontSize: 12.0,
      withLinesCount: true,
      selectable: true,
    );
  }
}

class ClassMembers extends StatefulWidget {
  final ZipEntry entry;

  const ClassMembers({super.key, required this.entry});

  @override
  State<ClassMembers> createState() => _ClassMembersState();
}

class _ClassMembersState extends State<ClassMembers> {
  final Client rpc = getIt<Client>();

  final members = <dynamic>[];

  @override
  void initState() {
    rpc.getFields(widget.entry.path).then((field) {
      setState(() {
        members.addAll(field);
      });
    });
    rpc.getMethods(widget.entry.path).then((method) {
      setState(() {
        members.addAll(method);
      });
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return TableView.builder(
      columns: [
        const TableColumn(width: 56.0, freezePriority: 100),
        const TableColumn(width: 0, flex: 1, freezePriority: 100),
        const TableColumn(width: 0, flex: 1, freezePriority: 100),
      ],
      headerBuilder: (context, contentBuilder) {
        return contentBuilder(
          context,
          (context, column) => Center(
            child: Text(() {
              switch (column) {
                case 0:
                  return "Type";
                case 1:
                  return "Name";
                case 2:
                  return "Descriptor";
              }
              return "";
            }()),
          ),
        );
      },
      rowCount: members.length,
      rowHeight: 56.0,
      rowBuilder: (context, row, contentBuilder) {
        return Material(
          type: MaterialType.transparency,
          child: InkWell(
            onTap: () => print('Row $row clicked'),
            child: contentBuilder(context, (context, column) {
              switch (column) {
                case 0:
                  return Text(members[row] == Field ? "F" : "M");
                case 1:
                  return Text(members[row].name);
                case 2:
                  return Text(members[row].desc);
              }

              return Placeholder();
            }),
          ),
        );
      },
    );
  }
}
