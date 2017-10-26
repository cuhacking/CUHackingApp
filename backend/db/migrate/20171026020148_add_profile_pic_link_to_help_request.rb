class AddProfilePicLinkToHelpRequest < ActiveRecord::Migration[5.1]
  def change
    add_column :help_requests, :profile_pic_link, :string
  end
end
