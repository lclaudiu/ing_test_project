  This demo applications let's the user calculate the value of different currencies based on the exchange rates provided by a server each second. 

### Informations about architecture
The app is based on the MVVM pattern and the recommended structure from Google: 

![GitHub Logo](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

Architecture components and libraries used inside the project:
- ViewModel for connecting UI with the data provided by the repository and preserve the
data when recreating the activity.
- LiveData to provide informations based on the lifecycle of different components as activities
and fragments.
- Data Binding for providing informations directly inside the xml
- Koin for dependency injection
- Retrofit 2 for network requests
- Coroutines for making network requests
- Glide for loading images
- Timber for logging
- Moshi for JSON’s activities
