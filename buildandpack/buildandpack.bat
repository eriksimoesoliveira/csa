call del /s /q "D:\Projetos\CS2Proj-Pack"
call gradlew clean
call gradlew dist
call java -jar buildandpack/packr-all-4.0.0.jar buildandpack/packr-config.json