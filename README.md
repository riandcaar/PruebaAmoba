# ARQUITECTURA PRINCIPAL

Se usó un modelo clásico de clean arquitectura divido en tres capas `UI`, `DOMAIN` y `DATA`.  
El patrón de diseño que se utilizó fue el de `MVI` por su compatibilidad con `Compose` y la ventaja  de  tener una sola fuente de verdad, importante para manejar listas fiables de pacientes y sus index,  ya que nuestra aplicación (o al menos este modulo de cobro) gira entorno a esto.


# FLUJO PRINCIPAL

El usuario se loggea a traves del `LoginScreen` con el usuario y la contraseña una vez es  
autenticado en `Firebase` se obtiene la lista de pacientes de ese usuario, se actualiza el estado y  
se guarda la lista de pacientes en la aplicación, de esta manera si en algún momento el usuario  
ingresa a la aplicación sin conexión a internet aún puede al menos cosultar su lista de pacientes y  
sus datos. Una vez selecciónado el paciente, navegamos al`PatientDetailScreen`, en donde podemos  
navegar a el mapa para ver su ubicación en `PatientLocationScreen` (hardcoded) o proceder a la ventana de  
pagos `PatientChargeScreen` y generar un cobro al servicio (datos hardcoded).

Usuario y contraseña:
> f.coronado838@gmail.com  
> 123456

El flujo general se puede resumir a  
screen -> viewmodel -> interfaz -> repositorio remoto o local -> servicio o bases datos.


# CONSIDERACIONES

El servicio está presentando un error:
"The requested resource does not support http method 'GET'"  
Que puede representar varias cosas, principalmente del lado del servicio, entonces se mockea un  tiempo de respuesta exitoso a los 10 segundos de generada la transacción para poder emular el flujo  exitoso y la reactividad de la vista de confirmación.


# HERRAMIENTAS PRINCIPALES

- `Dagger-Hilt` para la inyección de dependencias.
- `Firebase Authentication` para Login.
- `Firebase Firestore` la base de datos NoSQL.
- `Retrofit` para los llamados http.
- `Room` para la base de datos local (SQLite).
- `Google maps` para el mapa.
