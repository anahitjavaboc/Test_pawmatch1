import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private PetAdapter petAdapter;
    private List<Pet> petList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        petList = new ArrayList<>();

        // Sample Data
        petList.add(new Pet("Buddy", "https://your-image-url.com/dog1.jpg"));
        petList.add(new Pet("Mittens", "https://your-image-url.com/cat1.jpg"));
        petList.add(new Pet("Charlie", "https://your-image-url.com/dog2.jpg"));

        petAdapter = new PetAdapter(petList, this);
        viewPager.setAdapter(petAdapter);

        // Swipe listener
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == petList.size() - 1) {
                    // Load more pets or reset swiping
                }
            }
        });
    }
}
