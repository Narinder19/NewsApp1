package com.example.android.newsapp1;

public class News {
    /**
     * Title of the News item
     **/
    private String mTitle;
    /**
     * Name of the News item
     **/
    private String mName;
    /**
     * Publish date in milliseconds of the News item
     **/
    private String mPublicationDate;
    /**
     * Url of the News item
     **/
    private String mUrl;
    /**
     * Author's First Name of the News item.
     */
    private String mAuthor;

    /**
     * Construct a new News object.
     *
     * @param Title           is the title of the news
     * @param Name            is the Section name of the news
     * @param PublicationDate is date at which news was published
     * @param Url             is web url for the news item to find more details about the news
     */
    public News(String Title, String Name, String PublicationDate, String Url, String Author) {
        mTitle = Title;
        mName = Name;
        mPublicationDate = PublicationDate;
        mUrl = Url;
        mAuthor = Author;
    }

    /**
     * @return news title
     **/
    public String getTitle() {
        return mTitle;
    }

    /**
     * @return news Name
     **/
    public String getName() {
        return mName;
    }

    /**
     * @return news publication date
     **/
    public String getDate() {
        return mPublicationDate;
    }

    /**
     * @return news Url
     **/
    public String getUrl() {
        return mUrl;
    }

    /**
     * @return author of the news item.
     */
    public String getAuthor() {
        return (mAuthor);
    }
}
