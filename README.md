* Install latest version of or-tools from https://developers.google.com/optimization/install. (In this, I have used install from binary)

* Run `make test_java` after extracting above binary.

* Create your new maven project in suitable IDE.

* Copy `or-tools_MacOsX-10.15.4_v7.6.7691/lib` folder to the root directory of your maven project.

* In `pom.xml` add following dependencies :
        `<dependency>
            <groupId>com.google.ortools</groupId>
            <artifactId>ortools</artifactId>
            <version>1</version>
            <scope>system</scope>
            <systemPath>${basedir}/lib/com.google.ortools.jar</systemPath>
        </dependency>
        <dependency>
            <groupId>com.google.protobuf</groupId>
            <artifactId>protobuf</artifactId>
            <version>1</version>
            <scope>system</scope>
            <systemPath>${basedir}/lib/protobuf.jar</systemPath>
        </dependency>`
 
* Create `Example.java` file having or-tools code.

* To run the `Example.java` file add `-Djava.library.path={absolute_path_to_you_project_root_directory}/lib` in `Run`->`Edit Configurations`->`VM options` of your IntelliJ project.
