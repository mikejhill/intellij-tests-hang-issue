plugins {
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.7.0")
    testImplementation("org.junit.platform:junit-platform-engine:1.7.0")
    testImplementation("org.junit.platform:junit-platform-launcher:1.7.0")
}

project.tasks.withType(Test::class) {
    useJUnitPlatform { includeEngines("junit-jupiter", "junit-vintage") }
}

project.tasks.register("hangingTest", Test::class) {
    // Configuration-time filter: Succeeds, but test still runs since filter is overridden by ijtestinit.gradle
//    filter {
//        setIncludePatterns("NoMatchingTests")
//        isFailOnNoMatchingTests = false
//    }
    doFirst {
        // doFirst filter: Succeeds when matched; hangs when unmatched (overrides ijtestinit.gradle filter)
        filter {
//            setIncludePatterns("SampleTests") // Matched
            setIncludePatterns("NoMatchingTests") // Unmatched
            isFailOnNoMatchingTests = false
        }
    }
}
project.tasks.named("test", Test::class) {
    dependsOn("hangingTest")
}
