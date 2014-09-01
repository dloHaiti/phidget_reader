rm -rf ./bin
mkdir -p ./bin
javac -d bin -sourcepath src -classpath .:phidget21.jar src/com/dlohaiti/phidgets/*.java
cp PhidgetReader.mf ./bin/
pushd bin
jar cfm PhidgetReader.jar PhidgetReader.mf com/dlohaiti/phidgets/*.class
popd
echo "[DONE]"
