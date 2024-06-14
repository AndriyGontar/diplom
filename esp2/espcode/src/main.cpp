#include <Arduino.h>
#include <Adafruit_NeoPixel.h>
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
int outpin = 15;
Adafruit_NeoPixel matrix;
WiFiServer server(80);
//const char* ssid = "esp8266";
const char* ssid = "TP-Link_A0A4";
//const char* ssid = "Madera";
//const char* ssid = "Asus_K215";
//const char* ssid = "realme 6";
//const char* password ="qwertyui";
//const char* password = "Pillowo20242104";
//const char* password = "11111111";
const char* password = "ok1978ok";

uint16 col=0;

void setup() {
  pinMode(outpin, OUTPUT);
  matrix =Adafruit_NeoPixel(64,outpin);
  matrix.begin();
  matrix.setBrightness(50);
  matrix.show(); 
  Serial.begin(9600);
   WiFi.mode(WIFI_STA);
  //WiFi.mode(WIFI_AP);
//  WiFi.softAP(ssid,password);
  WiFi.begin(ssid,password);
   while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  IPAddress myIP = WiFi.localIP();
  Serial.println(myIP);
  WiFi.begin();
  server.begin(); // Start the TCP server
  Serial.println("TCP server started");
}

void loop() {
  WiFiClient client = server.available();
  if (client) {
    Serial.println("New Client Connected");
    uint8_t buffer[64] = {0};
    size_t bytesRead = 0;
    while (client.connected()) {
      if (client.available()) {
        bytesRead = client.read(buffer, sizeof(buffer));
        if (bytesRead == sizeof(buffer)) {
          Serial.println("Received Data:");
          for (size_t i = 0; i < bytesRead; i++) {
            Serial.print(buffer[i], HEX);
            Serial.print(" ");
          }
          Serial.println();
          for (size_t i = 0; i < 16; i++) {
            uint32_t color = buffer[9 + i * 3] << 16 | buffer[10 + i * 3] << 8 | buffer[11 + i * 3];
            matrix.setPixelColor(col%64, color);
            col++;
          }
          matrix.show();
          client.println("true");
        } else {
          client.println("false");
        }
      }
    }
    client.stop(); 
    Serial.println("Client Disconnected");
  }
}
  bool verifyChecksum(const uint8_t data[64]) {
    uint64_t checksum = 0;
    uint64_t storedChecksum = 0;
    for (int i = 0; i < 57; i++) {
        checksum += data[i];
    }
    for (int i = 0; i < 7; i++) {
        storedChecksum = (storedChecksum << 8) | data[57 + i];
    }
    return checksum == storedChecksum;
}


