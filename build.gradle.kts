plugins {
    id("java")
}

group = "com.codeborne"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
    minHeapSize = "32m"
    maxHeapSize = "32m"
    jvmArgs = listOf("-ea", "-Djunit.jupiter.extensions.autodetection.enabled=true")
}

defaultTasks("test")