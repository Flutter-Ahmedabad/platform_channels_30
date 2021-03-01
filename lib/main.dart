import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Page1(),
    );
  }
}

class Page1 extends StatelessWidget {
  static const platform =
      const MethodChannel('method_channel_1/messageChannel');
  static const EventChannel counterStream =
      EventChannel('EventChannel/counter');

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Page 1'),
      ),
      body: Container(
        child: Column(
          children: [
            RaisedButton(
              child: Text('Call'),
              onPressed: () {
                _invokePlatformCode('Hello');
              },
            ),
            StreamBuilder(
              stream: counterStream.receiveBroadcastStream(),
              builder: (context, snapshot) {
                if (snapshot.hasData) {
                  return Text(snapshot.data.toString());
                }
                return Text('NA');
              },
            ),
          ],
        ),
      ),
    );
  }

  Future<void> _invokePlatformCode([String message = "Default message"]) async {
    try {
      bool result = await platform.invokeMethod('displayMessage', message);
      print("Received result $result");
      print(result ? "Success" : "Failure");
    } on PlatformException catch (e) {
      print(e);
      print('Platform exception');
    }
  }
}
