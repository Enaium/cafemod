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
import 'package:cafemod/state/file_tree_state.dart';
import 'package:collection/collection.dart';
import 'package:fluent_ui/fluent_ui.dart';
import 'package:provider/provider.dart';

class FileTree extends StatefulWidget {
  const FileTree({super.key});

  @override
  State<StatefulWidget> createState() => _FileTreeState();
}

class _FileTreeState extends State<FileTree> {
  final Client rpc = getIt<Client>();

  TreeViewItem entry2Item(ZipEntry entry) {
    return TreeViewItem(
      key: Key(entry.path),
      content: GestureDetector(
        onDoubleTap: () {
          if (entry.type == ZipEntryType.FILE) {
            context.read<ContentTabState>().appendEntry(entry);
          }
        },
        child: Row(
          spacing: 5,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            WindowsIcon(
              entry.type == ZipEntryType.DIRECTORY
                  ? FluentIcons.fabric_folder
                  : FluentIcons.file_code,
              size: 24,
            ),
            Text(entry.name),
          ],
        ),
      ),
      lazy: entry.type == ZipEntryType.DIRECTORY,
      children: [],
      onExpandToggle: (item, _) async {
        await expand(item);
      },
    );
  }

  Future<void> expand(TreeViewItem item) async {
    final entry = await rpc.findEntry(item.key.toString().split("'")[1], 1);
    entry?.children
        ?.sorted((e1, e2) {
      return e1.type == ZipEntryType.DIRECTORY ? -1 : 1;
    })
        .forEach((entry) {
      final exists =
          item.children.firstWhereOrNull(
                (item) => item.key.toString().split("'")[1] == entry.path,
          ) !=
              null;

      if (!exists) {
        item.children.add(entry2Item(entry));
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<FileTreeState>(
      builder: (_, state, _) {
        return Container(
          decoration: BoxDecoration(
              border: Border.all(color: Color(0x10FFFFFF), width: 0.5),
              borderRadius: BorderRadius.circular(5)
          ),
          child: TreeView(
            items:
            state.entry?.children
                ?.map((entry) => entry2Item(entry))
                .toList() ??
                [],
          ),
        );
      },
    );
  }
}
