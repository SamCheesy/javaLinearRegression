# Práctica 3: Programación Orientada a Objetos - Regresión Lineal Múltiple

## Autores
Samuel Ruidiaz Camargo

Adrian Botero

---

## 1. Objetivos

- Aplicar conceptos básicos relacionados con la Programación Orientada a Objetos (POO).
- Desarrollar aplicaciones simples utilizando Java (o C++).
- Presentar el desarrollo de forma clara y organizada.

---

## 2. Descripción del proyecto

Esta práctica consiste en diseñar e implementar un **marco orientado a objetos** para calcular **modelos de regresión lineal múltiple** sin usar librerías externas. El objetivo es comprender el entrenamiento del modelo, la predicción, el escalado de datos y la evaluación del rendimiento.

La implementación se realizó en **Java**, usando una clase principal `LinearRegression` y una clase `Main` encargada de la lectura de archivos CSV, el entrenamiento y la evaluación de dos modelos:

1. **Regresión Lineal Simple:** utilizando el archivo `Ice_cream_selling_data.csv`.
2. **Regresión Lineal Múltiple:** utilizando el archivo `student_exam_scores.csv`.

---

## 3. Archivos incluidos

```
src/
 ├── LinearRegression.java
 ├── Main.java
 ├──data/
      ├── Ice_cream_selling_data.csv
      ├── student_exam_scores.csv
README.md
```

---

## 4. Instrucciones de ejecución

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/SamCheesy/javaLinearRegression.git
   cd LinearRegression-Practice3
   ```

2. Asegurarse de tener instalada una versión de **Java 17 o superior**.

3. Compilar y ejecutar el proyecto:
   ```bash
   javac src/*.java
   java -cp src Main
   ```

4. Verificar que la carpeta `data/` esté en la raíz del proyecto (al mismo nivel que `src/`).

---

## 5. Explicación Detallada del Código

### 5.1. Clase `LinearRegression`

Esta clase es el núcleo del proyecto. Contiene los métodos y atributos que permiten crear, entrenar y evaluar un modelo de regresión lineal.

#### Atributos Principales:
- **`weights`**: vector que almacena los coeficientes de la regresión (pendientes de cada variable). 
- **`bias`**: término independiente (constante) que ajusta el modelo. 

#### Métodos Principales:

##### 1. `fit(double[][] X, double[] y)`
- Este método **entrena el modelo**. 
- Recibe una matriz `X` (variables independientes) y un vector `y` (variable dependiente). 
- Calcula los coeficientes del modelo utilizando la **fórmula cerrada de la regresión lineal** basada en álgebra matricial:
  
  \[
  w = (X^T X)^{-1} X^T y
  \]
  
- Aquí se construyen manualmente las operaciones de multiplicación y transposición de matrices sin usar librerías externas.
- Finalmente, el método guarda los valores de `weights` y `bias` en los atributos de la clase.

##### 2. `predict(double[][] X)`
- Este método **genera las predicciones** para cada fila de la matriz `X`. 
- Realiza la multiplicación:
  
  \[
  \hat{y} = Xw + b
  \]
  
- Retorna un arreglo con los valores estimados (`ŷ`).

##### 3. `meanSquaredError(double[] yTrue, double[] yPred)`
- Calcula el **Error Cuadrático Medio (MSE)**:
  
  \[
  MSE = \frac{1}{n} \sum_{i=1}^n (y_i - \hat{y_i})^2
  \]
  
- Este valor mide qué tan lejos están las predicciones de los valores reales. 
- Cuanto menor sea el MSE, mejor el ajuste del modelo.

##### 4. `scaleData(double[][] X)`
- Este método **normaliza los datos** para evitar que las diferencias de escala afecten el entrenamiento. 
- Se resta la media y se divide por la desviación estándar de cada columna.

---

### 5.2. Clase `Main`

Esta clase gestiona la ejecución general del programa. Contiene el método `main()` y las funciones auxiliares para cargar y procesar los datos desde los archivos CSV.

#### Métodos Principales:

##### 1. `readCSV_X(String filePath)`
- Lee las columnas independientes (X) desde un archivo CSV. 
- Divide cada línea del archivo por comas y convierte las cadenas en valores numéricos. 
- Devuelve una matriz `double[][]`.

##### 2. `readCSV_y(String filePath)`
- Lee la última columna del archivo (variable dependiente Y). 
- Devuelve un arreglo `double[]`.

##### 3. `main(String[] args)`
- Es el punto de entrada del programa. 
- Ejecuta los siguientes pasos:
  1. Carga los datos del archivo CSV correspondiente. 
  2. Crea un objeto de la clase `LinearRegression`. 
  3. Escala los datos. 
  4. Entrena el modelo con el método `fit()`. 
  5. Realiza predicciones con `predict()`. 
  6. Calcula el MSE con `meanSquaredError()`. 
  7. Imprime en consola los resultados del modelo.

---

## 5. Flujo General del Programa

1. **Lectura de datos** 
   Los datos se leen desde la carpeta `data/`. Cada línea del CSV representa un ejemplo con una o más características.

2. **Preprocesamiento** 
   Se normalizan los datos de entrada para garantizar una mejor estabilidad numérica.

3. **Entrenamiento del modelo** 
   Se calculan los coeficientes de la regresión mediante operaciones matriciales implementadas manualmente.

4. **Predicción y evaluación** 
   Se generan los valores estimados y se evalúan comparándolos con los reales usando el MSE.

5. **Salida en consola** 
   El programa muestra:
   - Dimensiones de los datos cargados. 
   - Pesos y sesgo del modelo. 
   - Predicciones generadas. 
   - Error cuadrático medio obtenido.

---


## 6. Ejemplo de salida

```
==== REGRESIÓN LINEAL SIMPLE ====
Datos cargados (simple): 49 filas, 1 columna
Weights: [-0.3616541614049704]
Bias: -0.45324210119415115
Score (MSE): 413.54739113169217

==== REGRESIÓN LINEAL MÚLTIPLE ====
Datos cargados (múltiple): 200 filas, 4 columnas
Weights: [-0.21716043409710872, 0.6172645676803353, 0.016715653062308607, 0.5993619838207468]
Bias: 0.16328822688167022
Score (MSE): 1185.513720904273
```

---

## 7. Problemas y soluciones

`FileNotFoundException` El programa no encuentra los archivos CSV.
Solucion: Verificar que la carpeta `data/` esté en la raíz del proyecto y contenga los archivos. 
Valor alto de MSE. Las predicciones no se acercan mucho a los valores reales. Explicacion: Es normal en conjuntos de datos pequeños o con ruido. El modelo funciona matemáticamente.
Error en la inversión de matrices que ocurre cuando la matriz es singular. Solucion: Se agregó un valor pequeño (1e-9) para evitar divisiones por cero en el escalado.

---

## 8. Conclusiones

1. La programación orientada a objetos permite diseñar modelos matemáticos modulares y reutilizables. 
2. Implementar regresión desde cero fortalece la comprensión de operaciones matriciales y álgebra lineal. 
3. El escalado de datos mejora la estabilidad numérica y la precisión del modelo. 
4. Esta práctica integra la teoría matemática con la implementación computacional. 

---
