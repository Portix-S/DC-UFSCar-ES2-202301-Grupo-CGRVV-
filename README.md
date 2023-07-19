# DC-UFSCar-ES2-202301-Grupo-CGRVV-
Trabalho de Engenharia de Software 2 

Owner - Vitor Matheus da Silva

Intregrantes:
<Gabriel Lourenço de Paula Graton - 800432>
<Vitor Matheus da SIlva - 800260>
<Vitor Enzo Araujo Costa - 802123>
<Rodrigo Pavão Coffani Nunes - 800345>
<Cristian César Martins - 799714>

- Instalar o Java 20

https://www.oracle.com/java/technologies/downloads/

- Pedir para instalar o .deb e colocar no alternatives a referencia para o jdk_20
```
	sudo apt install ~/Downloads/jdk-20_linux-x64_bin.deb
	sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk-20/bin/java 1
	sudo update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/jdk-20/bin/javac 1
	sudo update-alternatives --install /usr/bin/jar jar /usr/lib/jvm/jdk-20/bin/jar 1
```


- Códigos para trocar versão do Java
```
	sudo update-alternatives --config java
	sudo update-alternatives --config javac
	sudo update-alternatives --config jar
```
Caso o Jabref não apareça no Eclipse:
- Ir até a pasta do jabref e rodar

```
	./gradlew run
	./gradlew eclipse
```


- Ao abrir o eclipse: Lembrar de mudar a versão em Preferences>Java>Installed JREs, se não aparecer o jdk 20, adicionar a pasta usr/lib/java/jdk20

- Procurar o arquivo org.jabref.cli.Launcher e apertar com o botão direito -> Run as > Run Config
- Em argumentos colocar em VM arguments:
```
     --add-exports javafx.controls/com.sun.javafx.scene.control=org.jabref
     --add-exports org.controlsfx.controls/impl.org.controlsfx.skin=org.jabref
     --add-exports javafx.graphics/com.sun.javafx.scene=org.controlsfx.controls
     --add-exports javafx.graphics/com.sun.javafx.scene.traversal=org.controlsfx.controls
     --add-exports javafx.graphics/com.sun.javafx.css=org.controlsfx.controls
     --add-exports javafx.controls/com.sun.javafx.scene.control.behavior=org.controlsfx.controls
     --add-exports javafx.controls/com.sun.javafx.scene.control=org.controlsfx.controls
     --add-exports javafx.controls/com.sun.javafx.scene.control.inputmap=org.controlsfx.controls
     --add-exports javafx.base/com.sun.javafx.event=org.controlsfx.controls
     --add-exports javafx.base/com.sun.javafx.collections=org.controlsfx.controls
     --add-exports javafx.base/com.sun.javafx.runtime=org.controlsfx.controls
     --add-exports javafx.web/com.sun.webkit=org.controlsfx.controls
     --add-exports javafx.graphics/com.sun.javafx.css=org.controlsfx.controls
     --add-exports javafx.controls/com.sun.javafx.scene.control.behavior=com.jfoenix
     --add-exports javafx.graphics/com.sun.javafx.stage=com.jfoenix
     --add-exports com.oracle.truffle.regex/com.oracle.truffle.regex=org.graalvm.truffle
     --patch-module org.jabref=build/resources/main
```

