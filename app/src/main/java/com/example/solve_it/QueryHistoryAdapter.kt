import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.solve_it.R
import com.example.solve_it.models.QueryHistory

class QueryHistoryAdapter(private val historyList: List<QueryHistory>) :
    RecyclerView.Adapter<QueryHistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskText: TextView = itemView.findViewById(R.id.taskTextView)
        val solutionText: TextView = itemView.findViewById(R.id.solutionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_query_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyList[position]
        holder.taskText.text = "Задача: ${item.taskText}"
        holder.solutionText.text = "Рішення:\n${item.solution}"
    }

    override fun getItemCount() = historyList.size
}
