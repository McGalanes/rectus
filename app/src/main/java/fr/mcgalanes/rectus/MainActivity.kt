package fr.mcgalanes.rectus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavGraph
import com.ramcosta.composedestinations.DestinationsNavHost
import fr.mcgalanes.rectus.feature.transactions.ui.list.NavGraphs
import fr.mcgalanes.rectus.feature.transactions.ui.list.TransactionListScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
                TransactionListScreen()
            }
        }
    }
}