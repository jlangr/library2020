# library_full

This source base requires JDK 11 or later.

Please don't hesitate to contact me at jeff @ langrsoft.com if you have any problems.

Disclaimer: Some of the source in the codebase deliberately stinks. Some of it stinks because, well, it's easy for all of us to write code we're soon not proud of. (No worries--we accept that reality and know that we can incrementally improve the code.)


IntelliJ IDEA Instructions (TDD class only)
---

* Import project: Select the build.gradle located in the root of this repo. You should be able to retain the defaults in the `Import Project from Gradle` dialog.
* From the dialog `Gradle Project Data To Import`, keep all modules selected and click OK.
* In the project tool window, expand library_full -> library -> src -> test -> junit.
* Select AllFastTests. Right-click and select Run `AllFastTests`.
* You should see at least 200 green unit tests, and they should run in a second or so at most.

Running the Application
---
./gradlew bootRun

Running the Cucumber Tests in IDEA
---

Make sure the application is running first!

Right-click on the `acceptanceTests` directory in the project explorer, and select `Run Tests in Library2020`.
