Physics Body Editor
==========
[![Maven Central](https://img.shields.io/maven-central/v/org.aya-prover.box2d-editor/loader)](https://repo1.maven.org/maven2/org/aya-prover/box2d-editor/loader)

<img src="readmeImgs/slide-physics-body-editor.jpg" alt="Physics Body Editor"/>

Introduction
-----

This project is a higher-order fork:
[MovingBlocks] → [simoarpe] → [ice1000]
For more information, please refer to the original project(s).

I'm just doing the obvious things:

* In the source code, I
  * Upgrade Gradle & LibGDX versions
  * Use Java 8 language features
  * Fix some obvious bugs, such as `for (int i = xxx.length; i >= 0; i--)`
* Project level, I
  * Provide prebuilt binaries
  * Upload the project to maven central

To add the runtime to in your project, use

```gradle
repositories { mavenCentral() }
dependencies {
  implementation 'org.aya-prover.box2d-editor:loader:3.0.0'
}
```

[ice1000]: https://github.com/ice1000/box2d-editor
[simoarpe]: https://github.com/simoarpe/box2d-editor
[MovingBlocks]: https://github.com/MovingBlocks/box2d-editor
