Rails.application.routes.draw do

  resources :users
  resources :events
  resources :rooms
  resources :buildings
  resources :companies
  resources :help_requests
  resources :notifications
  post "/help_requests/bot_callback", "help_requests#bot_callback" 


  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
