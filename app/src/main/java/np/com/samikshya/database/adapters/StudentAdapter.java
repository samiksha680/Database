package np.com.samikshya.database.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import np.com.samikshya.database.DbHelper;
import np.com.samikshya.database.EditDetailsActivity;
import np.com.samikshya.database.R;
import np.com.samikshya.database.models.Student;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder>{

    private Context context;
    private List<Student> studentList;

    public StudentAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_student_list, parent, false);
        return (new StudentViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, final int position) {
        final Student student = studentList.get(position);
        holder.txtName.setText(student.getName());
        holder.txtEmail.setText(student.getEmail());
        holder.txtPhone.setText(student.getPhone());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper db = new DbHelper(context);
                db.deleteStudent(student.getId());

                studentList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditDetailsActivity.class);
                intent.putExtra("id", String.valueOf(student.getId()));
                intent.putExtra("name", student.getName());
                intent.putExtra("email", student.getEmail());
                intent.putExtra("phone", student.getPhone());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtEmail, txtPhone;
        ImageView btnEdit, btnDelete;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            btnEdit = itemView.findViewById(R.id.imageEdit);
            btnDelete = itemView.findViewById(R.id.imageDelete);
        }
    }
}
