package cn.edu.heuet.pagingdemofinal_java.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cn.edu.heuet.pagingdemofinal_java.R;
import cn.edu.heuet.pagingdemofinal_java.model.Repo;

/**
 * @ClassName RepoViewHolder
 * @Author littlecurl
 * @Date 2020/1/8 13:31
 * @Version 1.0.0
 * @Description TODO
 */
public class RepoViewHolder extends RecyclerView.ViewHolder {
    private TextView name;
    private TextView description;
    private TextView stars;
    private TextView language;
    private TextView forks;

    private Repo repo;

    public RepoViewHolder(@NonNull View view) {
        super(view);
        name = view.findViewById(R.id.repo_name);
        description = view.findViewById(R.id.repo_description);
        stars = view.findViewById(R.id.repo_stars);
        language = view.findViewById(R.id.repo_language);
        forks = view.findViewById(R.id.repo_forks);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(repo.html_url));
                view.getContext().startActivity(intent);
            }
        });
    }
    public void bind(Repo repo){
        if (repo == null) {
            Resources resources = itemView.getResources();
            name.setText(resources.getString(R.string.loading));
            description.setVisibility(View.GONE);
            language.setVisibility(View.GONE);
            stars.setText(resources.getString(R.string.unknown));
            forks.setText(resources.getString(R.string.unknown));
        } else {
            showRepoData(repo);
        }
    }

        private void showRepoData (Repo repo){
        this.repo = repo;
        name.setText(repo.full_name);

        // if the description is missing, hide the TextView
        int descriptionVisibility = View.GONE;
        if (repo.description != null) {
            description.setText(repo.description);
            descriptionVisibility = View.VISIBLE;
        }
        description.setVisibility(descriptionVisibility);

        stars.setText(String.valueOf(repo.stargazers_count));
        forks.setText(String.valueOf(repo.forks_count));

        // if the language is missing, hide the label and the value
        int languageVisibility = View.GONE;
        if (!TextUtils.isEmpty(repo.language)) {
            Resources resources = this.itemView.getContext().getResources();
            language.setText(resources.getString(R.string.language, repo.language));
            languageVisibility = View.VISIBLE;
        }
        language.setVisibility(languageVisibility);
    }

        public static RepoViewHolder create (ViewGroup parent){

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.repo_view_item, parent, false);
            return new RepoViewHolder(view);
        }
}
