

package vcmsa.uvayharichand.mealapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    private lateinit var timeInput: EditText
    private lateinit var mealSuggestion: TextView
    private lateinit var suggestButton: Button
    private lateinit var resetButton: Button
    private lateinit var mealImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // UI components
        timeInput = findViewById(R.id.timeInput)
        mealSuggestion = findViewById(R.id.mealSuggestion)
        suggestButton = findViewById(R.id.suggestButton)
        resetButton = findViewById(R.id.resetButton)
        mealImage = findViewById(R.id.mealImage)

        // Initially disable the Suggest button
        suggestButton.isEnabled = false

        // Enable Suggest button only when input is provided
        timeInput.addTextChangedListener {
            val inputText = timeInput.text.toString().trim()
            suggestButton.isEnabled = inputText.isNotEmpty()
        }

        // Set up the Suggest button click listener
        suggestButton.setOnClickListener {
            val timeOfDay = timeInput.text.toString().trim().lowercase()
            val meal = suggestMeal(timeOfDay)

            if (meal != null) {
                mealSuggestion.text = "Meal suggestion: $meal"
                updateMealImage(meal)
            } else {
                Toast.makeText(this, "Please enter a valid time of day.", Toast.LENGTH_SHORT).show()
                mealImage.visibility = ImageView.GONE
            }
        }

        // Set up the Reset button click listener
        resetButton.setOnClickListener {
            timeInput.text.clear()
            mealSuggestion.text = "Meal suggestion will appear here"
            mealImage.setImageResource(0)
            mealImage.visibility = ImageView.GONE
            suggestButton.isEnabled = false
        }
    }

    // Function to return meal suggestion based on the time of day
    // use of AI to generate the meal suggestion (chatgpt)
    private fun suggestMeal(timeOfDay: String): String? {
        return when (timeOfDay) {
            "morning" -> "Eggs, bacon and toast"
            "mid-morning" -> "Fruit salad"
            "afternoon" -> "Sandwich of choice"
            "mid-afternoon" -> "Chocolate, caramel, or vanilla cake"
            "dinner" -> "Chicken piccata with garlicky greens & new potatoes"
            "after dinner" -> "Ice cream"
            else -> null
        }
    }

    // Function to update the image based on the meal suggestion
    private fun updateMealImage(meal: String) {
        val imageResId = when (meal) {
            "Eggs, bacon and toast" -> R.drawable.breakfast
            "Fruit salad" -> R.drawable.fruit
            "Sandwich of choice" -> R.drawable.sandwich
            "Chocolate, caramel, or vanilla cake" -> R.drawable.cake
            "Chicken piccata with garlicky greens & new potatoes" -> R.drawable.dinner
            "Ice cream" -> R.drawable.ice
            else -> 0
        }

        if (imageResId != 0) {
            mealImage.setImageResource(imageResId)
            mealImage.visibility = ImageView.VISIBLE
        } else {
            mealImage.visibility = ImageView.GONE
        }
    }
}
