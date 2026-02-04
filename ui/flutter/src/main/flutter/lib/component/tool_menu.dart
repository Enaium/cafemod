import 'package:cafemod/extension/client_impl.dart';
import 'package:cafemod/state/file_tree_state.dart';
import 'package:file_picker/file_picker.dart';
import 'package:fluent_ui/fluent_ui.dart';
import 'package:flutter/material.dart' hide MenuBar;
import 'package:provider/provider.dart';
import 'package:url_launcher/url_launcher.dart';

import 'package:cafemod/di/di.dart';
import 'package:cafemod/rpc/client.dart';

class ToolMenu extends StatefulWidget {
  const ToolMenu({super.key});

  @override
  State<StatefulWidget> createState() => _ToolMenuState();
}

class _ToolMenuState extends State<ToolMenu> {
  final Client rpc = getIt<Client>();

  @override
  Widget build(BuildContext context) {
    return MenuBar(
      items: [
        MenuBarItem(
          title: "File",
          items: [
            MenuFlyoutItem(
              text: const Text("Load File"),
              onPressed: () async {
                FilePickerResult? result = await FilePicker.platform.pickFiles(
                  dialogTitle: "Choose a jar/zip file",
                  type: FileType.custom,
                  allowedExtensions: ['jar', 'zip'],
                );

                if (result != null) {
                  context.read<FileTreeState>().changeEntry(
                    await rpc.loadFiles(result.files.single.path!),
                  );
                }
              },
            ),
          ],
        ),
        MenuBarItem(
          title: "Help",
          items: [
            MenuFlyoutItem(
              text: const Text("About"),
              onPressed: () async {
                showAboutDialog(
                  context: context,
                  applicationName: "Cafemod",
                  applicationVersion: "1.0.0",
                  applicationLegalese: "@Enaium",
                  children: [
                    Text("UI: Flutter, Controller: Kotlin"),
                    GestureDetector(
                      onTap: () {
                        launchUrl(Uri.https("github.com", "Enaium/cafemod"));
                      },
                      child: Row(
                        children: [
                          Text("GitHub"),
                          Text(
                            "https://github.com/Enaium/cafemod",
                            style: TextStyle(
                              decoration: TextDecoration.underline,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ],
                );
              },
            ),
          ],
        ),
      ],
    );
  }
}
