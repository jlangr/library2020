Java 11 => 14

  don't forget Gradle config


add to build.gradle:


   testImplementation 'org.junit.jupiter:junit-jupiter-api'
   testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine'
   testImplementation('org.springframework.boot:spring-boot-starter-test') {
       exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
   }


By class:

- remote all org.junit imports

- rename Before to BeforeEach

- use jupiter imports

- remove "public" from class and test declarations


AssertJ

assertThat static import


JUnit:

        assertThat(normalizer.normalize(""), equalTo(""));

assertJ:


        assertThat(normalizer.normalize("")).isEmpty();

  %s/), equalTo("\(.*\)"));/)).isEqualTo("\1");/


Exceptions:

    @Test(expected = IllegalArgumentException.class)

    AssertJ:
      
        assertThatThrownBy(() -> {
            normalizer.normalize("Thurston, Howell, III");
        }).isInstanceOf(IllegalArgumentException.class);

