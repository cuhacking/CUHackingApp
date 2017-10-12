class AddNameToEvent < ActiveRecord::Migration[5.1]
  def change
    add_column :events, :name, :string
  end
end
