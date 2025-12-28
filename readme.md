# Control App

粉色主题的 Android 应用，中心为跳动红心，圆环向外扩张。

## 环境要求

- Android Studio Hedgehog 或更新版本
- Android SDK 34
- JDK 17

## 本地运行

1. 使用 Android Studio 打开项目根目录 `/workspace/control-app`。
2. 等待 Gradle 同步完成。
3. 选择一个模拟器或真机，点击 **Run** 运行。

## 部署（安装到设备）

1. 确认设备已开启开发者模式并允许 USB 调试。
2. 连接设备后，执行：

```bash
./gradlew :app:installDebug
```

安装完成后，在设备上找到 **Control App** 并打开。

## 打包

### Debug APK

```bash
./gradlew :app:assembleDebug
```

生成路径：`app/build/outputs/apk/debug/app-debug.apk`

### Release APK

```bash
./gradlew :app:assembleRelease
```

生成路径：`app/build/outputs/apk/release/app-release.apk`

> 如需发布到商店，请在 `app/build.gradle.kts` 中配置签名信息后再打包 Release。
