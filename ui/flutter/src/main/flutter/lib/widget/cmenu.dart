import 'package:flutter/material.dart';

class CMenu extends StatefulWidget {
  const CMenu({super.key, required this.menus});

  final List<Menu> menus;

  @override
  State<StatefulWidget> createState() => _CMenuState();
}

class _CMenuState extends State<CMenu> {
  @override
  Widget build(BuildContext context) {
    return Row(
      children: widget.menus.map((menu) {
        return PopupMenuButton<MenuItem>(
          offset: const Offset(0, 48),
          child: Padding(
            padding: const EdgeInsets.symmetric(
              horizontal: 16.0,
              vertical: 12.0,
            ),
            child: Text(menu.name, style: const TextStyle(color: Colors.white)),
          ),
          onSelected: (item) {
            item.action();
          },
          itemBuilder: (context) {
            return menu.items.map((item) {
              return PopupMenuItem<MenuItem>(
                value: item,
                child: Text(item.name),
              );
            }).toList();
          },
        );
      }).toList(),
    );
  }
}

class Menu {
  final String name;
  final List<MenuItem> items;

  Menu({required this.name, required this.items});
}

class MenuItem {
  final String name;
  final Function() action;

  MenuItem({required this.name, required this.action});
}
