# Trabajo práctico 3 - Algoritmos genéticos

### Julián Benítez, Nicolás Marcantonio y Eliseo Parodi Almaraz

## Compilación

Para compilar el trabajo práctico hay que usar el siguiente comando en la carpeta que contiene el pom.xml, en 
**TP3/geneticwarrior**

```
mvn clean compile assembly:single
```

Esto crea una carpeta **target** con un archivo llamado **geneticwarrior-1.0-SNAPSHOT-jar-with-dependencies.jar**.
Este archivo contiene toda la información necesaria para correr el programa.

## Ejecución

Para poder ejecutar el programa es importante tener en la carpeta **TP3/geneticwarrior/resources** dos carpetas que van 
a contener los archivos con los datos de los ítems de los personajes. La distribución de los archivos en esta carpeta
es la siguiente:

* resources/
    * fulldata/
        * armas.tsv
        * botas.tsv
        * cascos.tsv
        * guantes.tsv
        * pecheras.tsv
    * testdata/
        * armas.tsv
        * botas.tsv
        * cascos.tsv
        * guantes.tsv
        * pecheras.tsv

En la misma carpeta donde se encontraba antes **TP3/geneticwarrior**, para correr el programa debe correr el siguiente
comando:

```
java -jar target/geneticwarrior-1.0-SNAPSHOT-jar-with-dependencies.jar <path al archivo de configuración>
```

En la carpeta **TP3/geneticwarrior/resources/** se encuentran algunos ejemplos de archivos de configuración.

## Archivo de configuración

El archivo de configuración es un JSON. Los objetos que componen a este JSON son:

* "name" (str): nombre del experimento. Cualquier string arbitrario funciona aquí.
* "dataset" (str): indica si se va a usar el dataset de prueba o el que contiene los datos reales. Este parametro acepta
 sólo dos valores:
    * "full": el dataset con todos los valores.
    * "test": el dataset de prueba.
* "breeder" (str): Es el algoritmo que se va a utilizar para cruzar a los individuos. Los valores son.
    * "anular": cruce anular.
    * "simple": cruce simple.
    * "twopoint": cruce de dos puntos.
    * "uniform": cruce uniforme parametrizado.
* "mutator" (str): indica el tipo de método para generar mutaciones.
    * "evolving": el método no uniforme. Con este valor el json debe recibir valores adicionales.
        * "startRatio" (num): La probabilidad inicial de mutación.
        * "endRatio" (num): La probabilidad final de mutación.
        * "duration" (num): Cuantas generaciones debe haber para pasar de la probabilidad inicial a la final.
    * "uniform": método uniforme. Con este valor el json debe recibir también los siguientes valores.
        * "chanceToMutate" (num): La probabilidad de que se realice la mutación.
        * "mutationStrength" (num): Valor que controla que tanto puede aumentar o disminuir la altura de un individuo 
        cuando este muta
* "selector" (str | obj): el método de selección.
    * "elite": elite
    * "roulette": roulette
    * "squared": roulette con los fitness elevados al cuadrado
    * "boltzmann": boltzmann
    * "tournament": torneo determinístico. Este parametro exige además que el json incluya el siguiente parámetro:
        * "tourneySize" (int): el tamaño del torneo.
    * "randomTournament": torneo estocástico
    * "ranking": ranking
    * "random": random
    * si es un objeto, usa configuración mixta y el objeto recibe los siguientes parametros:
        * "first" (str): nombre de un método de selección de los previamente mencionados.
        * "second" (str): al igual que "first", exige el nombre de un método de selección.
        * "firstToTotalRatio" (num): proporción de individuos elegidos con el primer método. 
* "replacement" (str): aplican los mismo valores que "selector".
* "experiment" (str): indica el método de reemplazo.
    * "simple": método de reemplazo 1.
    * "normal": método de reemplazo 2. Este campo requiere también agregar un valor más:
        * "parentRatio": El porcentaje de padres que se eligen.
    * "complex": método de reemplazo 3. Este campo también requiere otro valor:
        * "parentRatio": El porcentaje de padres que se eligen.
* "amount"(int): cantidad de individuos en la población.
* "type" (str): el tipo de personaje:
    * "warrior": guerrero.
    * "archer": arquero.
    * "defender": defensor.
    * "asesino": asesino.
* "variant" (int): número del 1 al 3 que indica que personaje se va a usar.

Condiciones de corte:

* "targetFitness" (int): el experimento termina cuando se alcanza un fitness determinado.
* "maxStaleBestFitnessGenerations" (int): cuantas generaciones el fitness se mantiene constante antes de que corte.
* "maxStalePopulation" (int): cuantos individuos con las mismas caracteristicas acepta antes de terminar.
* "maxGenerations" (int): el experimento corta cuando se alcanza un número de generaciones.