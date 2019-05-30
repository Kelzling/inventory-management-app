package nz.ac.ara.kev38.inventorymanager.modelcode;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag implements Parcelable {
	private String name;
	private TagType type;
	private String otherInfo;
	
	public Tag(String newName, TagType newType, String newOtherInfo)
	{
		this.name = newName;
		this.type = newType;
		this.otherInfo = newOtherInfo;
	}

	protected Tag(Parcel in) {
		name = in.readString();
		type = TagType.valueOf(in.readString());
		otherInfo = in.readString();
	}

	public static final Creator<Tag> CREATOR = new Creator<Tag>() {
		@Override
		public Tag createFromParcel(Parcel in) {
			return new Tag(in);
		}

		@Override
		public Tag[] newArray(int size) {
			return new Tag[size];
		}
	};

	public void setName(String newName)
	{
		this.name = newName;
	}
	
	public void setOtherInfo(String newOtherInfo)
	{
		this.otherInfo = newOtherInfo;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public TagType getType()
	{
		return this.type;
	}
	
	public String getOtherInfo()
	{
		return this.otherInfo;
	}
	
	public boolean equals(Tag otherTag)
	{
		return this.name == otherTag.name && this.type == otherTag.type;
	}

	@Override
	public String toString() {
		return this.name + "," + this.type + "," + this.otherInfo;
	}

	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(name);
		parcel.writeString(type.name());
		parcel.writeString(otherInfo);
	}
}
