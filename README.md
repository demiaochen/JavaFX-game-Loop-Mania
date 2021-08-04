# COMP2511 Major Project: Loop Mania

[**Link to specification**](https://gitlab.cse.unsw.edu.au/COMP2511/21T2/project-specification)
<br />
<br />
<br />
<br />

## Link to UML lucidchart
https://lucid.app/lucidchart/5c188d42-305c-4cab-977f-56f8734a6a87/edit?shared=true&page=HWEp-vi-RSFO# 
<br />
<br />

## Java Coverage Tester
https://cgi.cse.unsw.edu.au/~cs2511/21T2/coverage/UI/app.py

Use this web app, very use ful

And the group name is:  T15A_AERO
<br />
<br />

## Installing JavaFX on your own system

Delete the *symlink_javafx* symbolic link, then download and unzip the latest version of the JavaFX JDK for Java 11 for your Operating System (taking into account if you have a 64 or 32 bit machine), and transfer the contents of the *lib* folder inside the JDK download into the *lib* folder on your cloned repository. You will also need to change the [*launch.json*](.vscode/launch.json) file to refer to **"./lib"** instead of **./lib/symlink_javafx** in the *"vmArgs"* configuration (note these modifications were tested on Windows 10) as per below;

```diff
{
    "configurations": [
        {
            "type": "java",
            "name": "Launch CheckerApplication",
            "request": "launch",
            "mainClass": "unsw.crown.CheckerApplication",
            "projectName": "lab04_acb2551e",
-           "vmArgs": "--module-path ./lib/symlink_javafx --add-modules javafx.controls,javafx.fxml,javafx.media -enableassertions"
+           "vmArgs": "--module-path ./lib --add-modules javafx.controls,javafx.fxml,javafx.media -enableassertions"
        }
    ]
}
```

You may also need to copy the contents of the *bin* folder in the unzipped JavaFX JDK download into a *bin* folder under the root directory of your cloned repository (e.g. for Windows).

The following version of the JavaFX JDK is recommended if you choose to run it on your computer, since it is the same version as on the CSE machine:

https://gluonhq.com/products/javafx/

Note that if you deviate from this precise directory structure, you may need to modify the VSCode configuration in [*launch.json*](.vscode/launch.json) to be able to run the game in VSCode.

If these steps worked (and you setup java, and the recommended VSCode extensions properly), you should be able to run the starter code game.

## `JavaFX Runtime Components are missing`

If running locally make sure that you've followed the steps above.  If running on VLab/CSE, then make sure that ALL launch configurations in `.vscode/launch.json` contain the line;

```json
"vmArgs": "--module-path ./lib/symlink_javafx --add-modules javafx.controls,javafx.fxml,javafx.media -enableassertions"
```

It's just VSCode creating new configurations rather than recycling the old ones.  This seems 
# JavaFX-game-Loop-Mania
