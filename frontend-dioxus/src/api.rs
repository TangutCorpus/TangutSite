use chrono::{DateTime, Local};
use dioxus::logger::tracing::debug;
use serde::Deserialize;

#[derive(Debug, Deserialize, Clone, PartialEq, Eq)]
#[serde(rename_all = "camelCase")]
pub struct Text {
    pub id: Option<i32>,
    pub comment: String,
    pub line_ids: Vec<i32>,
    pub pure_text: String,
    pub created_at: Option<DateTime<Local>>,
}

pub async fn search(query: String) -> Option<Vec<Text>> {
    debug!("/search");
    let client = reqwest::Client::new();
    let query = if query.is_empty() {
        vec![]
    } else {
        vec![("query", "comment=in=".to_owned() + &query)]
    };
    client
        .get("http://localhost:8080/search")
        .query(&query)
        .send()
        .await
        .ok()?
        .json()
        .await
        .ok()
}

pub async fn get_by_id(id: &i32) -> Option<Text> {
    debug!("/get_by_id");
    reqwest::get("http://localhost:8080/texts/".to_owned() + &id.to_string())
        .await
        .ok()?
        .json()
        .await
        .ok()
}
