# Tablut_AI_Player

Java implementation of an Ashton Tablut AI Player using the Aima Library. This project has been developed to partecipate to an accademic competion of "Fondamenti D'Intelligenza Artificiale" course of Computer Engineering in Bologna.

Ashton Tablut game and challange rules:

  - https://github.com/bonacciog/Tablut_AI_Player/blob/docs-ita/Challenge2019.pdf
- - -

  - To run the black client execute from line command **with default setting**:
  
```
$ java -jar LordKesaniIlDioDellaDistruzione.jar -P=BLACK
```

Or:
      
    $ java -jar AimaTablutBlackPlayer.jar 
      
Or **double click** on ***AimaTablutBlackPlayer.jar***
- - -

  - To run the white client execute from line command **with default setting**:
 ```
$ java -jar LordKesaniIlDioDellaDistruzione.jar -P=WHITE 
 ```
Or:
``` 
$ java -jar AimaTablutWhitePlayer.jar 
```
Or **double click** on ***AimaTablutWhitePlayer.jar***
- - -
- To run the client **without default setting** execute  ***LordKesaniIlDioDellaDistruzione.jar*** with the previous command with:

specifying a name:
```
-N=name
```  
specifying a maximum response time (example with 60 seconds):
```
-t=60 
```
without opening moves:
```
-OM=NOOP
```
- - -

Some part of this project has been taken from:

  - https://github.com/AGalassi/TablutCompetition

Aima library (3.0.0):

  - https://github.com/aimacode/aima-java



GSON library:
  - https://github.com/google/gson

...particularly:

  - https://github.com/google/gson/releases/tag/gson-2.2.2



- - -

_Developed by team composed by [bonacciog](https://github.com/bonacciog), [PaoloCaligiana](https://github.com/PaoloCaligiana), [allefossa9663](https://github.com/allefossa9663), [Bananito96](https://github.com/Bananito96)_


