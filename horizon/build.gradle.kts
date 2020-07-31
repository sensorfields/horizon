plugins {
    kotlin("jvm")
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
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")

    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    testImplementation("com.google.truth:truth:1.0.1")
}
