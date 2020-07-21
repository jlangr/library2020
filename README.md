# library2020

This source base requires JDK 11 or later.

Contact me at jeff @ langrsoft.com if you have any problems.

Loading the Project in IDEA
---

Import using the build.gradle file located in the root of this repo. The defaults should work.

Running the Application
---

./gradlew bootRun

Running the Cucumber Tests in IDEA
---

Make sure the application is running first!

Right-click on the `acceptanceTests` directory in the project explorer, and select `Run Tests in Library2020`.


Running the Unit Tests: IntelliJ IDEA Instructions (TDD class only)
---

* In the project tool window, expand library_full -> library -> src -> test -> junit.
* Select AllFastTests. Right-click and select Run `AllFastTests`.
* You should see at least 200 green unit tests, and they should run in a second or so at most.


Disclaimer
---

Some of the source in the codebase deliberately stinks. Some of it stinks because, well, it's easy for all of us to write code we're soon not proud of. (No worries--we accept that reality and know that we can incrementally improve the code.)
x