#!/bin/bash
# Script que lee el código fuente Java y genera documentación HTML automáticamente.
# Utiliza el plugin maven-javadoc-plugin configurado en el pom.xml.
# La documentación generada queda en: invernadero-back/target/site/apidocs/index.html

echo "Leyendo código fuente y generando documentación..."

cd invernadero-back
./mvnw javadoc:javadoc --quiet

echo "Documentación generada en: invernadero-back/target/site/apidocs/index.html"
