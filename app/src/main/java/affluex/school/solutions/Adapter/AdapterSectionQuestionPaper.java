package affluex.school.solutions.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import affluex.school.solutions.Model.ModelQuestionPaperSection;
import affluex.school.solutions.R;

public class AdapterSectionQuestionPaper extends RecyclerView.Adapter<AdapterSectionQuestionPaper.HolderSectionQuestionPaper> {

    private Context context;
    private ArrayList<ModelQuestionPaperSection> questionPaperSectionArrayList;

    public AdapterSectionQuestionPaper(Context context, ArrayList<ModelQuestionPaperSection> questionPaperSectionArrayList) {
        this.context = context;
        this.questionPaperSectionArrayList = questionPaperSectionArrayList;
    }

    @NonNull
    @Override
    public AdapterSectionQuestionPaper.HolderSectionQuestionPaper onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_section_single_row, parent, false);
        return new AdapterSectionQuestionPaper.HolderSectionQuestionPaper(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSectionQuestionPaper.HolderSectionQuestionPaper holder, int position) {
        ModelQuestionPaperSection modelQuestionPaperSection=questionPaperSectionArrayList.get(position);
        holder.txt_marks.setText(modelQuestionPaperSection.getSectionMarks());
        holder.txt_name.setText(modelQuestionPaperSection.getSectionHeading());
        holder.img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return questionPaperSectionArrayList.size();
    }

    public class HolderSectionQuestionPaper extends RecyclerView.ViewHolder {
        TextView txt_name,txt_marks;
        ImageView img_next;
        public HolderSectionQuestionPaper(@NonNull View itemView) {
            super(itemView);

            txt_marks=itemView.findViewById(R.id.txt_marks);
            txt_name=itemView.findViewById(R.id.txt_name);
            img_next=itemView.findViewById(R.id.img_next);
        }
    }
}
