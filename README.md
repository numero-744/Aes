# AES: a spinal demo project

- From the template
- Added `.scalafmt.conf` for formatting, from the one of `SpinalHDL` but with only 80 columns (default) and with alignments :smiley:
- Added `project/plugin.sbt` for formatting via `sbt`
- Added `.vscode/SpinalHDL.code-snippets` for VSCode snippets
- Updated project name in folder name and `build.*`
- Using `SpinalHDL@dev`
- I had several generators for several entities at first, so I had put them all in `Generators.scala`.
- Updated `.gitignore`

Use of snippets are put in comments. It is "full" use, so actually there was less keystrokes because of auto completion :wink:

- Simple example of component in `hw/spinal/aes/AddRoundKey.scala`.
- More snippets, shown in `hw/spinal/aes/Aes.scala`, with idioms.
- Disabling formatting locally shown in `hw/spinal/aes/SBox.scala`.
- Component-as-function snippet shown in `hw/spinal/aes/SBox.scala`.

- [x] `sbt scalafmtCheckAll` passed so **all code is actual auto formatting**.
- [x] `sbt publishLocal` has correct name: in `build.sbt`, `name := ` is not needed, it gets the value name