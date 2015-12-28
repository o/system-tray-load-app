### System tray remote load application

Super simple app that shows remote Linux host load as System tray icon. Tested on Mac OS X, but looks like
compatible with MS Windows and Linux X.

![Screenshot](https://raw.githubusercontent.com/o/system-tray-load-app/master/screenshot.png)

##### Requirements

* Java 1.8

##### Building

    mvn clean package

##### Running

Remote host parameters passed as environment args

     L_HOST=hostname.foo L_USER=root L_PASSWORD=P4$$w0rD java -jar target/loadapp-1.0-SNAPSHOT.jar

##### License

The MIT License (MIT)

Copyright (c) 2015 Osman Ungur

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
