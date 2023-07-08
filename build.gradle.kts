/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * To learn more about Gradle by exploring our Samples at https://docs.gradle.org/8.2/samples
 */

dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

repositories {
  mavenCentral()
}

plugins {
  application
  java
  kotlin("jvm") version "1.9.0"
}

application {
  mainClass.set("endgame.Endgame")
}

tasks.named<Test>("test") {
  useJUnitPlatform()

  maxHeapSize = "1G"

  testLogging {
      events("passed")
  }
}
