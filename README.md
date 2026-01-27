# Cafemod

A Java bytecode viewer.

## 🛠️ Technology Stack

### Backend
- **[Kotlin](https://kotlinlang.org/)** - Modern language for the JVM
- **[ASM](https://asm.ow2.io/)** - Java bytecode manipulation and analysis framework
- **[Saucer](https://saucer.app/)** - Webview for desktop applications

### Frontend
- **[Vue 3](https://vuejs.org/)** - Progressive JavaScript framework
- **[Naive UI](https://www.naiveui.com)** - Vue 3 component library
- **[Monaco Editor Vue3](https://bazingaedward.github.io/monaco-editor-vue3)** - Code editor component
- **[Vite](https://vitejs.dev/)** - Next generation frontend tooling

## 📁 Project Structure

```
cafemod/
├── core/                 # Bytecode analysis engine
│   └── src/
│       ├── main/        # Core bytecode processing logic
│       └── test/        # Unit tests
├── ui/                  # Desktop application UI
│   ├── src/
│   │   ├── main/        # Kotlin backend for UI
│   │   └── vue/         # Vue 3 frontend application
│   └── build/           # Built assets
├── gradle/              # Gradle wrapper and build configuration
└── build.gradle.kts     # Root build configuration
```

## 📸 Screenshot

![](https://s2.loli.net/2026/01/27/iVySegrMnUWcY3w.png)
![](https://s2.loli.net/2026/01/27/158HTq7MI6cEoZA.png)
![](https://s2.loli.net/2026/01/27/o9SqzFvuKeiyPTx.png)