INSERT INTO blog(id, name, url, rss_url) VALUES (1, 'blog name1', 'blog url1', 'blog rss url1');

INSERT INTO post(id, blog_id, title, link, published_date) VALUES (1, 1, 'post title1', 'post link1', '2020-08-26 02:35:31');
INSERT INTO post(id, blog_id, title, link, published_date) VALUES (2, 1, 'post title2', 'post link2', '2020-08-27 02:35:31');