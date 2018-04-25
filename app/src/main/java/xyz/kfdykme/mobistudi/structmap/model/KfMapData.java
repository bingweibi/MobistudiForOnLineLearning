package xyz.kfdykme.mobistudi.structmap.model;

import java.util.*;

import xyz.kfdykme.mobistudi.structmap.view.StructView;

public class KfMapData<T>
{
	T data;
	List<KfMapData<T>> pre = new ArrayList<KfMapData<T>>();
	List<KfMapData<T>> nex = new ArrayList<KfMapData<T>>();

	StructView.KfMapNodeView view;
	
	public KfMapData(T data)
	{
		this.data = data;

	}

	public void setView(StructView.KfMapNodeView view)
	{
		this.view = view;
	}

	public StructView.KfMapNodeView getView()
	{
		return view;
	}


	public void setData(T data)
	{
		this.data = data;
	}

	public T getData()
	{
		return data;
	}

	public void setPre(List<KfMapData<T>> pre)
	{
		this.pre = pre;
	}

	public List<KfMapData<T>> getPre()
	{
		return pre;
	}

	public void setNex(List<KfMapData<T>> nex)
	{
		this.nex = nex;
	}

	public List<KfMapData<T>> getNex()
	{
		return nex;
	}


}
