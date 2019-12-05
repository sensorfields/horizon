plugins {
    kotlin("jvm") version "1.3.61"
}

tasks {
    test {
        useJUnitPlatform()
    }
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk7"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")

    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
    testImplementation("com.google.truth:truth:1.0")
}
