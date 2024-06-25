package affluex.school.solutions.ExamAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import affluex.school.solutions.ExamModel.Question;
import affluex.school.solutions.Fragments.AddQuestionFragment;
import affluex.school.solutions.R;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private ArrayList<Question> questionList;
    private AdapterView.OnItemClickListener listener;

    public QuestionAdapter(ArrayList<Question> questionList, AdapterView.OnItemClickListener listener) {
        this.questionList = questionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.sectionHeading.setText(question.getSectionHeading());
        holder.sectionMarks.setText(question.getSectionMarks());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.onItemClick(AddQuestionFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView sectionHeading, sectionMarks;
        ImageView nextIcon;

        QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionHeading = itemView.findViewById(R.id.tv_section_heading);
            sectionMarks = itemView.findViewById(R.id.tv_section_marks);
            nextIcon = itemView.findViewById(R.id.img_next);
        }
    }
}
