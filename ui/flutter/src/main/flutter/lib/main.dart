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

import 'package:cafemod/component/tool_menu.dart';
import 'package:cafemod/di/di.dart';
import 'package:cafemod/rpc/client.dart';
import 'package:cafemod/state/content_tab_state.dart';
import 'package:cafemod/state/file_tree_state.dart';
import 'package:fluent_ui/fluent_ui.dart';
import 'package:multi_split_view/multi_split_view.dart';
import 'package:provider/provider.dart';
import 'package:system_theme/system_theme.dart';

import 'component/content_tab.dart';
import 'component/file_tree.dart';

void main() async {
  await configureDependencies();
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => FileTreeState()),
        ChangeNotifierProvider(create: (_) => ContentTabState()),
      ],
      child: const MyApp(),
    ),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    final color = AccentColor.swatch({
      'darkest': SystemTheme.accentColor.darkest,
      'darker': SystemTheme.accentColor.darker,
      'dark': SystemTheme.accentColor.dark,
      'normal': SystemTheme.accentColor.accent,
      'light': SystemTheme.accentColor.light,
      'lighter': SystemTheme.accentColor.lighter,
      'lightest': SystemTheme.accentColor.lightest,
    });

    return FluentApp(
      title: "Cafemod",
      home: const Home(),
      color: color,
      darkTheme: FluentThemeData(
        brightness: Brightness.dark,
        accentColor: color,
        visualDensity: VisualDensity.standard,
        focusTheme: FocusThemeData(
          glowFactor: is10footScreen(context) ? 2.0 : 0.0,
        ),
      ),
      theme: FluentThemeData(
        accentColor: color,
        visualDensity: VisualDensity.standard,
        focusTheme: FocusThemeData(
          glowFactor: is10footScreen(context) ? 2.0 : 0.0,
        ),
      ),
    );
  }
}

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  final Client rpc = getIt<Client>();

  @override
  void initState() {
    rpc.start();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return NavigationView(
      titleBar: TitleBar(title: const ToolMenu()),
      content: Expanded(
        child: MultiSplitView(
          controller: MultiSplitViewController(
            areas: [
              Area(flex: 1, builder: (context, area) => const FileTree()),
              Area(flex: 2, builder: (context, area) => const ContentTab()),
            ],
          ),
        ),
      ),
    );
  }

  @override
  void dispose() {
    rpc.stop();
    super.dispose();
  }
}
