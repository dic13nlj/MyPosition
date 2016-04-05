# MyPosition
På appens startsida visas två knappar. Dessa dirigerar användaren till två olika nya aktiviteter beroende på vilken knapp som är tryckt.
"Get position"- knapppen dirigerar till Action2Activity. Denna aktivitet visar en ny tom sida med en ny knapp "Get position". Denna knapp fungerar på så sätt att den hämtar mobilens GPS position med hjälp av mobilens gps funktion ("GPS_PROVIDER"). Positionen återges i rätta [X,Y,Z] värden som visas på displayen. Jag har för denna aktivitet kopierat kod från följande källa:
https://github.com/ihrupin/samples/blob/master/android/Android_GPS_Using/src/com/hrupin/HelloAndroidGpsActivity.java

Startsidans andra knapp "Compass" dirigerar användaren till ActionActivity. Där visas en kompass som visar den nuvarande riktningen. Nuvarande bäring visas även på displayen och uppdateras löpande. Båda delar är synkroniserade med mobilens sensorer och med en listener funktion bestämmer den korrekta bäringen med hjälp av att behandla olika aspekter som gravitation och magnetiska fält. Jag har för denna aktivitet kopierat kod från följande källa (med vissa småjusteringar):
https://github.com/iutinvg/compass/blob/master/app/src/main/java/com/sevencrayons/compass/Compass.java
