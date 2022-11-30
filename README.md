# AES: a spinal demo project

- From the template
- Added `.scalafmt.conf` for formatting, from the one of `SpinalHDL` but with only 80 columns (default) and with alignments :smiley:
- Added `project/plugin.sbt` for formatting via `sbt`
- Added `.vscode/SpinalHDL.code-snippets` for VSCode snippets
- Updated project name in folder name and `build.*`
- Using `SpinalHDL@dev`
- Updated `.gitignore`
- Added `tb/` folder with same structure as `hw/`, for tests / simulation files
- Updated `build.*` with the new `tb/` folder and to add HTML report generation (in `simWorkspace/test-reports`)

Use of snippets are put in comments. It is "full" use, so actually there was less keystrokes because of auto completion :wink:

- Simple example of component in `hw/spinal/aes/AddRoundKey.scala`.
- More snippets, shown in `hw/spinal/aes/Aes.scala`, with idioms.
- Disabling formatting locally shown in `hw/spinal/aes/SBox.scala`.
- Component-as-function snippet shown in `hw/spinal/aes/SBox.scala`.
- `App` to simulate is in `tb/spinal/aes/Simulate.scala`.
- `SpinalTestBench` is in `tb/spinal/aes/Simulate.scala`.
- `gtkw` configurations are generated for each test, in `tb/gtkw/`, from `tb/gtkw/Aes.gtkw` using my `deriveGtkw` script.

As a result I get:

- `simWorkspace/aes/aes should cipher example from class.fst` from the `SpinalTestBench`
- `simWorkspace/test-reports/index.html` from `SpinalTestBench`

![2022-11-30-193857_1918x1062_scrot](https://user-images.githubusercontent.com/42908717/204881904-560dbd43-5111-4cd7-98e4-7e2e3ada5921.png)

- [x] `sbt scalafmtCheckAll` passed so **all code is actual auto formatting**.
