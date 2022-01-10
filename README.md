# udacity_kotlin

## Project Setup

### 1. Data Binding

i) Enable data-binding in app module gradle

```gradle
android {
...

    enalbed : true
        dataBinding {
    }
}
dependencies {
...
}
```

sync project.

ii) Wrap your XML file in a layout tag, cut and paste the namespace declaration in our layout tag.

iii) Create a variable above onCreate() for the binding object. Usually called binding and it is initiated in the onCreate().
The name is derived from the name of the layout file plus binding.

```kotlin
private lateinit var binding: ActivityMainBinding
```

Import data binding class.
Replace setContentView() via the DataBindingUtil

```kotlin
binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
```

Refresh data by calling invalidateAll() in the binding object.
Remove binding redundancy in code by calling binding.apply {}

### 2. Updating view layouts

Access a clickable view and setOnClickListener { myFunction(it)}, pass the View via the it paramenter.
Access view item in the function like so.

```kotlin
binding.button.setOnClickListener {
addNickName(it)
}

private fun addNickName(View: view) {
binding.button = view.VISIBLE
}
```

### 3. Data Binding

i) Create a data class

```kotlin
data class MyName(
    var name: String = "",
    var nickname: String = ""
)
```

ii) Add a data block in the XML file

```xml
<data>
    <variable
         name = "myName"
          type= "com.example.android.aboutme.fileName"/>
</data>
```

iii) Reference the variable like so

```xml
<TextView
android:text="@={myName.name}"/>
```

iv) Set the instance of the data class

```kotlin
private val myName: MyName = MyName("Peter Musembi")

override fun onCreate(savedInstance: Bundle) {
//...
binding.myName = myName
}
//...
myName.nickName = binding.nameField.toString()
```

### 4. Layout

Use constrained layout for complex UI.
Make use of chaining and ratios.
Use the design section to make layout.
When configuring view binding wrap xml class in Layout tag and cut and paste namespace declaration in the name tag.
In order to refresh UI with new data call kt invalidateAll()
Use binding.apply(),if calling many attributes via bind in a concentrated section.

### 5. Fragments and Activity

With fragments an activity is entry point of the app, as the android OS can only open activity.
In fragment you manually inflate withing the onCreateView()
Activity operates as frame that contains UI fragments and can provide UI elements surrounding fragments.
Activity operates as frame that contains UI fragments and can provide UI elements surrounding fragments.
```kotlin
override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                         savedInstanceState: Bundle?): View? {
   val binding = DataBindingUtil.inflate<FragmentTitleBinding>(inflater, R.layout.fragment_title, container, false)
   return binding.root
}
```

Since activity extends the ContextCompact class, there is need to extend the context within a fragment to
have access to app data associated with the context such a string.
Define entry point fragment in the activity_main

```xml
<fragement
android:id="@+id/tileFragement"
android:name="com.example.android.navigation.filename"
android:layout_wodth="match_parent"
android:layout_height="match_parent"
/>
```

### 6. Navigation

#### Steps

i) Adding the Navigation Graph Resource which will provide the NavHostFragement
Rgt click res folder, New, Android resource file, Select Navigation Resource type.

ii) Add NaVHost fragment to our activity_main
app:defaultNavHost="true" // Means it will intercept the back key.

```xml
<!-- The NavHostFragment within the activity_main layout -->
<fragment
   android:id="@+id/myNavHostFragment"
   android:name="androidx.navigation.fragment.NavHostFragment"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   app:navGraph="@navigation/navigation"
   app:defaultNavHost="true" />
```

iii) Add fragments to the navigation graph.
Open navigation>navigation.xml>design tab> Add fragments

iv) Connect fragments in the graph with actions
hover and drag to connect.

v) Set onClick listener
vi) Find instance of a navigation controller

```kotlin
//The complete onClickListener with Navigation
binding.playButton.setOnClickListener { view: View ->
        view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
}
//The complete onClickListener with Navigation using createNavigateOnClickListener
binding.playButton.setOnClickListener(
        Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment))

```

#### Popping behaviour

Select the action, Add the pop To item in Pop Behaviour on the nagivation design tab,
selecting inclusive removes the popTo screen, the vice versa

### Adding Menu

1. Add necessary fragement to the navigation graph.
2. Create new menu resource(right click the res folder).
3. Create "About" menu item with ID of aboutFragment destination
4. Tell android that our TileFragement has a menu
```kotlin
    override fun onCreateView(...) { 
       setHasOptionsMenu(true) ...
    }
```
5. Override onCreateOptionMenu and inflate menu resource
```kotlin
   override ... { ... inflater?.inflate(R.menu.overflow_menu, menu)}
```
6. Override onOptionsItemSelected and call NavigationUI.onNavDestinationSelected
```kotlin
   override .. {
        return Navigation.onNavDestinationSelected(item!!, view!!.findNaveConroller()) || ...
```
### Implementing safe arguments.

1. Add safe-args dependency
```groovy
     dependencies {
     …
     "android.arch.navigation:navigation-safe-args-gradle-plugin:$version_navigation"
     } 
```
2. Add safeargs plugin to app gradle file
```groovy
     apply plugin: 'kotlin-kapt'
     apply plugin: 'androidx.navigation.safeargs'
```
3. Switch the GameFragment to use generated NavDirections when navigating to the GameOver and GameWon fragments.
4. Add the numQuestions and numCorrect Integer Arguments using the navigation editor
5. Add the parameters to the gameFragment to gameWonFragment action
```kotlin
   view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions, questionIndex))
```
6. Fetch the args and expand into a class in onCreate within the GameWonFragment
7. Display the arguments using a Toast
```kotlin
     val args = GameWonFragmentArgs.fromBundle(arguments!!)
     Toast.makeText(context, "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}", Toast.LENGTH_LONG).show()
```
8. Replace navigation to action IDs with NavDirections since safe arguments is in use


### Intents
-----
Explicit intents: used to launch a specific activity.
Implicit intents: used to launch activities exposed by other applications.

Intents have action, category and type, data type
All activities must be declared in manifest,
Explicit intents can be declared with only activity tag, but implicit must also have intent-filter inside the activity.