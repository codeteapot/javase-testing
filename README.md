[![Update](https://github.com/codeteapot/javase-testing/workflows/Update/badge.svg)](https://github.com/codeteapot/javase-testing/actions?query=workflow%3AUpdate)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.codeteapot.testing/javase-testing?label=Maven%20Central)](https://repo1.maven.org/maven2/com/github/codeteapot/testing/javase-testing/)

# JavaSE Testing

Visit [project site](https://codeteapot.github.io/javase-testing/v0.1.6-SNAPSHOT) to see full
documentation.

## Create new release

Execute the following commands,

```sh
mvn -f release-tag/pom.xml exec:exec@upgrade -DnewVersion=<new_version>
git push origin main
git push origin v<new_version>
```

Edit contents of the newly created release draft and save it to trigger its delivery process.
