package com.example.fragmenttransitions;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Displays a grid of pictures
 *
 * @author bherbst
 */
public class GridFragment extends Fragment implements KittenClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(new KittenGridAdapter(6, this));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public void onKittenClicked(KittenViewHolder holder, int position) {
        int kittenNumber = (position % 6) + 1;

        DetailsFragment kittenDetails = DetailsFragment.newInstance(kittenNumber);

        // Note that we need the API version check here because the actual transition classes (e.g. Fade)
        // are not in the support library and are only available in API 21+. The methods we are calling on the Fragment
        // ARE available in the support library (though they don't do anything on API < 21)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // TODO: 8/21/16 1 - set share ele enter và return là giống nhau 
            kittenDetails.setSharedElementEnterTransition(new DetailsTransition());
            kittenDetails.setSharedElementReturnTransition(new DetailsTransition());
            // TODO: 8/21/16 2 - set enter and exit transition là giống nhau 
            kittenDetails.setEnterTransition(new Fade());
            setExitTransition(new Fade());
        }

        /** TODO: 8/21/16 3 - nhớ lệnh addShareElement nếu ta sd share element
         * todo 3b chú ý addSharedElement(view frag1, tên transition frag 2)
         *
         */
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(holder.image, "kittenImage")
                .replace(R.id.container, kittenDetails)
                .addToBackStack(null)
                .commit();
    }
}
