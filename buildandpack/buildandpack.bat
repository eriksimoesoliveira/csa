call del /s /q "D:\Projetos\CS2Proj-Pack"
call gradlew clean
call gradlew dist
call java -jar packr-all-4.0.0.jar packr-config.json