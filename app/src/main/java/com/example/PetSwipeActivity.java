//class PetSwipeActivity : AppCompatActivity() {
//
//    override fun; onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_pet_swipe)
//
//        // Initialize CardStackView
//        val cardStackView = findViewById<CardStackView>(R.id.cardStackView)
//
//                // Set layout manager with swipe listener
//                val cardStackManager = CardStackLayoutManager(this, object : CardStackListener {
//            override fun onCardSwiped(direction: Direction) {
//                if (direction == Direction.Right) {
//                    Toast.makeText(this@PetSwipeActivity, "Liked!", Toast.LENGTH_SHORT).show()
//                } else if (direction == Direction.Left) {
//                    Toast.makeText(this@PetSwipeActivity, "Disliked!", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            // Optional: Override more callbacks as needed
//            override fun onCardDragging(direction: Direction, ratio: Float) {}
//            override fun onCardRewound() {}
//            override fun onCardCanceled() {}
//            override fun onCardAppeared(view: View?, position: Int) {}
//            override fun onCardDisappeared(view: View?, position: Int) {}
//        })
//
//        // Assign layout manager to CardStackView
//        cardStackView.layoutManager = cardStackManager
//
//        // Create sample pet data
//        val pets = listOf(
//                Pet("Buddy", "Loves playing fetch", R.drawable.ic_pet_placeholder),
//                Pet("Mittens", "Enjoys catnip and naps", R.drawable.ic_pet_placeholder),
//                Pet("Charlie", "Great at tricks", R.drawable.ic_pet_placeholder)
//        )
//
//        // Set up the adapter with sample data
//        cardStackView.adapter = PetCardAdapter(pets)
//    }
//}
