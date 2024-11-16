rmdir /S /Q ".\build\"
mkdir build
javac -d ./build -cp .;./lib/mariadb-java-client-3.5.0.jar ./src/cruds/gestaoPedidos/estoque/*.java ./src/cruds/receita/*.java 
java -cp .;./build;./lib/mariadb-java-client-3.5.0.jar %1